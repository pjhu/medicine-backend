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
```
密码在docker-compose.yml中
