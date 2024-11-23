# Nginx 이미지를 기반으로 사용
FROM nginx:alpine

# 컨테이너에서 8080 포트를 외부로 노출
EXPOSE 80

# 로컬에서 빌드된 dist 폴더를 Nginx의 웹 루트 디렉토리로 복사
COPY ./dist /usr/share/nginx/html

# Nginx의 기본 설정을 덮어쓸 수 있도록 nginx.conf 파일을 복사 (선택 사항)
COPY nginx.conf /etc/nginx/nginx.conf

# Nginx를 포그라운드 모드로 실행하여 컨테이너가 종료되지 않도록 함
CMD ["nginx", "-g", "daemon off;"]