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

# 4) stop.sh 실행
if [ -x "${DEPLOY_DIR}/stop.sh" ]; then
  echo "[INFO] stop.sh 실행..."
  bash "${DEPLOY_DIR}/stop.sh" || echo "[WARN] stop.sh 실행 중 오류 (무시)"
else
  echo "[WARN] stop.sh 파일이 없거나 실행 권한이 없습니다."
fi

sleep 3

# 5) start.sh 실행
if [ -x "${DEPLOY_DIR}/start.sh" ]; then
  echo "[INFO] start.sh 실행..."
  bash "${DEPLOY_DIR}/start.sh"
else
  echo "[ERROR] start.sh 파일이 없거나 실행 권한이 없습니다."
  exit 1
fi

echo "===== DEPLOY END: $(date '+%Y-%m-%d %H:%M:%S') ====="

