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

exec > >(tee -a "${DEPLOY_LOG}") 2>&1

echo "[INFO] WAR_NAME=${WAR_NAME}"
echo "[INFO] BUILD_WAR=${BUILD_WAR}"

if [ ! -f "${BUILD_WAR}" ]; then
  echo "[ERROR] Build WAR not found: ${BUILD_WAR}"
  exit 1
fi
echo "[INFO] Build war 확인됨."

if [ -f "${DEPLOY_DIR}/${WAR_NAME}" ]; then
  BACKUP_FILE="${BACKUP_DIR}/${WAR_NAME%.war}_${TIMESTAMP}.war"
  cp "${DEPLOY_DIR}/${WAR_NAME}" "${BACKUP_FILE}"
  echo "[INFO] 기존 WAR 백업됨: ${BACKUP_FILE}"
else
  echo "[INFO] 백업할 기존 WAR 없음."
fi

cp "${BUILD_WAR}" "${DEPLOY_DIR}/${WAR_NAME}"
echo "[INFO] 새 WAR 배포 경로로 복사 완료."

echo "[INFO] 기존 프로세스 종료 중..."
pkill -f "${WAR_NAME}" && echo "[INFO] 프로세스 종료 완료." || echo "[INFO] 종료할 프로세스 없음."
sleep 3

echo "[INFO] 애플리케이션 시작..."

cd "${DEPLOY_DIR}"

if [ -f "${DEPLOY_DIR}/.env" ]; then
  echo "[INFO] 환경 변수 로드 중 (.env)"
  set -a
  source "${DEPLOY_DIR}/.env"
  set +a
fi

nohup java -jar "${DEPLOY_DIR}/${WAR_NAME}" --spring.profiles.active=prod \
  > "${DEPLOY_DIR}/application.log" 2>&1 &

NEW_PID=$!
echo "[INFO] 새 프로세스 PID: ${NEW_PID}"
echo "===== DEPLOY END: $(date '+%Y-%m-%d %H:%M:%S') ====="

