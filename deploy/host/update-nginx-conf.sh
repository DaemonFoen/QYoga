cd ../server || exit
ansible-playbook -v -i ./hosts -u root ./qyoga-server.yaml --tags "configure-nginx"
ssh root@trainer-advisor.pro "sudo systemctl restart nginx.service"
