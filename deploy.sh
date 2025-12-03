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

# 배포 로그에 출력 남기기
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

# 4) 기존 프로세스 죽이기 (원래 stop.sh 로 하던 것과 동일)
echo "[INFO] 기존 프로세스 종료 중..."
pkill -f "${WAR_NAME}" && echo "[INFO] 프로세스 종료 완료." || echo "[INFO] 종료할 프로세스 없음."
sleep 3

# 5) "지금 수동으로 잘 되는 방식" 그대로 실행
echo "[INFO] 애플리케이션 시작 (직접 java -jar 방식)..."
cd "${DEPLOY_DIR}"

# 필요하면 .env 로드 (기존 start.sh처럼)
if [ -f "${DEPLOY_DIR}/.env" ]; then
  echo "[INFO] 환경 변수 로드 중 (.env)"
  set -a
  source "${DEPLOY_DIR}/.env"
  set +a
fi

nohup java -jar "${WAR_NAME}" --spring.profiles.active=prod \
  > "${DEPLOY_DIR}/application.log" 2>&1 &

NEW_PID=$!
echo "[INFO] 새 프로세스 PID: ${NEW_PID}"

# 6) 상태 확인 + 최근 로그 조금 보여주기
sleep 3
echo "[INFO] 현재 java 프로세스 목록:"
ps -ef | grep "${WAR_NAME}" | grep -v grep || echo "[WARN] 프로세스 안 떠있음"

echo "[INFO] 최근 application.log:"
tail -n 50 "${DEPLOY_DIR}/application.log" || echo "[INFO] application.log 없음"

echo "===== DEPLOY END: $(date '+%Y-%m-%d %H:%M:%S') ====="

