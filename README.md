## Start postgres
```$xslt
cd docker
docker-compose up -d
```

## Create database
```$xslt
创建数据库的脚本位于src/resources/db/create_database.sql。

用支持PG的客户端，运行该文件即可创建数据库。
```

### 用client连接到数据库

```
psql -h postgres -U postgres
密码在docker-compose.yml中
```


### Ldap
```$xslt
login: http://0.0.0.0:5080/phpldapadmin
login DN: cn=admin,dc=pjhu,dc=org
login pass: 123
```

##### ldap test
```$xslt
docker exec medicine-openldap ldapsearch -x -H ldap://localhost -b dc=pjhu,dc=org -D "cn=admin,dc=pjhu,dc=org" -w 123
```



