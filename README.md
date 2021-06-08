## 1. 开发环境

- JDK环境：JDK 11
- 使用 Intellij IDEA 的 import 导入该项目
- 使用 ./gradlew 执行 gradle 任务

## 2. 技术栈

- Spring boot
- Gradle
- Spring MVC
- Spring Security
- Flyway

## 3. 初次运行

## Start mysql
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
mysql -p
密码在docker-compose.yml中
```

### Ldap
```$xslt
login: http://0.0.0.0:5080/phpldapadmin
login DN: cn=admin,dc=pjhu,dc=org
login pass: 123
```

##### create user
1. create ou "medicine"
2. create group "medicine-admin"
3. create user "jinhu peng"

##### ldap test
```$xslt
docker exec medicine-openldap ldapsearch -x -H ldap://localhost -b dc=pjhu,dc=org -D "cn=admin,dc=pjhu,dc=org" -w 123
```
<!--dn: cn=jinhu peng,cn=medicine-admin,ou=medicine,dc=pjhu,dc=org-->

##### git commit conventions
config username: use firstnamelastname
config email: use thoughtworks email
command like:
```
git config --global user.name 'pengjinhu'
git config --global user.email 'jhpeng@thoughtworks.com'
```

valid :[MEM-110] - create order
valid:[MEM-120] - add spring security config
valid(technical card)​:[TECH] - refactor create order
invalid(no '-' )​: [MEM-110] refactor create order
invalid(wrong car number)​: [110] - add strategy timeseries 
invalid(no message)​:[MEM-110] 
invalid(meaningless message)​:[MEM-108] - update

- 代码的变化必须体现在测试用例中，不得提交没有测试覆盖的代码
- 必须用git pull --rebase 同步代码
            
##### 分布式锁
存储为hash, key为: LOCK::medicine:storage
field："8d836c0a-e6b7-4b84-8231-fb82a8bb43e8:78" 是uuid + thread-id
value: "1"

##### cache
默认使用SimpleCacheConfiguration, 使用ConcurrentMapCacheManager 存储信息，没有过期时间
使用ConcurrentMapCache中ConcurrentMap保存数据，put写数据，使用lookup查找数据
需要使用CacheEvict移除