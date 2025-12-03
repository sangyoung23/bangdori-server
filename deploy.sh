#!/bin/bash
set -e

echo "===== DEPLOY START: $(date '+%Y-%m-%d %H:%M:%S') ====="

WAR_NAME="api-0.0.1-SNAPSHOT.war"
BUILD_WAR="build/libs/${WAR_NAME}"

DEPLOY_DIR="/home/user/backend"
BACKUP_DIR="/home/user/backup"
LOG_DIR="/home/user/deploy-logs"

TIMESTAMP=$(date +"%Y%m%d_%H%M%S")
DEPLOY_LOG="${LOG_DIR}/deploy_${TIMESTAMP}.log"

mkdir -p "${BACKUP_DIR}"
mkdir -p "${LOG_DIR}"

# 배포 로그 파일에 모든 출력 쌓기
exec > >(tee -a "${DEPLOY_LOG}") 2>&1

echo "[INFO] BUILD_WAR=${BUILD_WAR}"

# 1) 빌드 산출물 체크
if [ ! -f "${BUILD_WAR}" ]; then
  echo "[ERROR] Build war not found: ${BUILD_WAR}"
  exit 1
fi
echo "[INFO] Build war 확인됨."

# 2) 기존 WAR 백업
if [ -f "${DEPLOY_DIR}/${WAR_NAME}" ]; then
  BACKUP_FILE="${BACKUP_DIR}/${WAR_NAME%.war}_${TIMESTAMP}.war"
  cp "${DEPLOY_DIR}/${WAR_NAME}" "${BACKUP_FILE}"
  echo "[INFO] 기존 WAR 백업됨: ${BACKUP_FILE}"
else
  echo "[INFO] 백업할 기존 WAR 없음."
fi

# 3) 새 WAR 복사
cp "${BUILD_WAR}" "${DEPLOY_DIR}/${WAR_NAME}"
echo "[INFO] 새 WAR 배포 경로로 복사 완료."

# 4) 기존 프로세스 종료
echo "[INFO] 기존 프로세스 종료 중..."
pkill -f "${WAR_NAME}" && echo "[INFO] 프로세스 종료 완료." || echo "[INFO] 종료할 프로세스 없음."
sleep 3

# 5) 애플리케이션 시작 (완전히 백그라운드로 분리)
echo "[INFO] 애플리케이션 시작..."
cd "${DEPLOY_DIR}"

# .env 로드 (원래 start.sh에서 하던 것과 동일)
if [ -f "${DEPLOY_DIR}/.env" ]; then
  echo "[INFO] 환경 변수 로드 중 (.env)"
  set -a
  source "${DEPLOY_DIR}/.env"
  set +a
fi

# *** 핵심: nohup + & 로 Jenkins와 완전히 분리 ***
nohup java -jar "${WAR_NAME}" --spring.profiles.active=prod \
  >> "${DEPLOY_DIR}/application.log" 2>&1 &

NEW_PID=$!
echo "[INFO] 새 프로세스 PID: ${NEW_PID}"

sleep 3

echo "[INFO] 현재 java 프로세스 목록:"
ps -ef | grep "${WAR_NAME}" | grep -v grep || echo "[WARN] 프로세스 안 떠있음"

echo "===== DEPLOY END: $(date '+%Y-%m-%d %H:%M:%S') ====="

