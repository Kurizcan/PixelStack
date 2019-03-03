# 接口说明

## 用户

### 获取用户信息详细列表


    method:GET
    http://localhost:8080/user/getUserInfo?uid=103   

    return:
            {
                "userInfo": {
                    "star": 0,
                    "like": 0,
                    "authority": "user",
                    "follow": 0,
                    "email": "YangMi@gmail.com",
                    "introduction": "fuck",
                    "username": "Yangmi",
                    "fans": 0
                },
                "status": "200"
            }

### 修改用户信息

    method:POST
    http://localhost:8080/user/modify

    params:
            username:Yangmi
            password:666666
            email:YangMi@gmail.com
            uid:102
            introduction:fuckkkkk

    return:
            {
                "message": "修改成功",
                "status": "200"
            }

            {
                "message": "密码已修改，请重新登陆",
                "status": "201"
            }

            {
                "message": "信息修改不规范",
                "status": "500"
            }        

### 登陆

    method:POST
    http://localhost:8080/user/login
 
    params:
        username:Yangmi
        password:666

    return:
            {
                "message": "用户不存在或者密码不正确",
                "status": "500"
            }

            {
                "message": "账户被冻结",
                "status": "501"
            }

            {
                "message": "账户被冻结",
                "status": "502"
            }

            {
                "userInfo": {
                    "uid": 102,
                    "authority": "user",
                    "email": "YangMi@gmail.com",
                    "introduction": "fuckkkkk",
                    "username": "Yangmi",
                    "status": "normal",
                    "token": "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIxMDIifQ.vBxQgxouKAg2X9eg8eXMLUiKAvVEoYROwxDQvWXS8q4"
                },
                "status": "200"
            }

            {
                "message": "token 创建或设置失败，请稍后重试",
                "status": "500"
            }        

### 注册

    method:POST
    http://localhost:8080/user/register
 
    params:
        username:Dawe
        email:lcan@gmail.com
        password:888

    return:
            {
                "message": "注册成功",
                "status": "200"
            }

            {
                登陆失败直接走向服务器 500 错误，这里不做错误信息提示
            }

### 上传照片

    method:POST
    http://localhost:8080/user/upload

    form-data: file (可批量)

    params:
        uid:104
        title:YangMi

    return:
            {
                "fail": 0,
                "upload": [
                    {
                        "iid": 165,
                        "title": "YangMi",
                        "author": "kobe",
                        "upload": "2019-03-03T08:40:48.223+0000",
                        "url": "C:\\Users\\asus\\Desktop\\users\\kobe\\2019-03-03\\original\\IMG_20170222_195652.jpg",
                        "count": 0
                    },
                    {
                        "iid": 166,
                        "title": "YangMi",
                        "author": "kobe",
                        "upload": "2019-03-03T08:40:48.223+0000",
                        "url": "C:\\Users\\asus\\Desktop\\users\\kobe\\2019-03-03\\original\\IMG_20170224_154250.jpg",
                        "count": 0
                    },
                    {
                        "iid": 167,
                        "title": "YangMi",
                        "author": "kobe",
                        "upload": "2019-03-03T08:40:48.223+0000",
                        "url": "C:\\Users\\asus\\Desktop\\users\\kobe\\2019-03-03\\original\\IMG_20170224_175425.jpg",
                        "count": 0
                    },
                    {
                        "iid": 168,
                        "title": "YangMi",
                        "author": "kobe",
                        "upload": "2019-03-03T08:40:48.223+0000",
                        "url": "C:\\Users\\asus\\Desktop\\users\\kobe\\2019-03-03\\original\\IMG_20170223_171111.jpg",
                        "count": 0
                    },
                    {
                        "iid": 169,
                        "title": "YangMi",
                        "author": "kobe",
                        "upload": "2019-03-03T08:40:48.223+0000",
                        "url": "C:\\Users\\asus\\Desktop\\users\\kobe\\2019-03-03\\original\\IMG_20170222_125824.jpg",
                        "count": 0
                    }
                ],
                "success": 5,
                "errors": [],
                "status": "200"
            }    

            发生上传大小超过阀值等错误时，将出现 500 状态

## 管理员

### 对用户账号状态进行管理

    method:POST
    http://localhost:8080/admin/manageCountStatus

    status: normal/frozen/terminate

    params(application/json): 
            {
                "101": "frozen",
                "102": "frozen",
                "103": "frozen", 
                "104": "frozen",
                "105": "frozen"
            }

    return:
            {
                "message": "修改状态成功",
                "status": 200
            }

            {
                "message": "修改状态失败",
                "status": 200
            }    


### 创建管理员账号

    method:POST
    http://localhost:8080/admin/createCount

    params: 
        username:Curry
        email:lcan@gmail.com
        password:888
        authority:xxx（只有超级管理员 root 角色才被允许）

    return:
            {
                "message": "创建成功",
                "status": 200
            }

            {
                "message": "修改失败",
                "status": 500
            } 

### 获取用户信息列表

    method:GET
    http://localhost:8080/admin/getUserList

    params:
        type:1 (管理员用户信息) 0 (普通用户信息)

    return:
            {
                "user List": [
                    {
                        "uid": 101,
                        "username": "Lcanboom",
                        "authority": "user",
                        "email": "lcan@gmail.com",
                        "status": "frozen"
                    },
                    {
                        "uid": 102,
                        "username": "Yangmi",
                        "authority": "user",
                        "email": "YangMi@gmail.com",
                        "status": "frozen"
                    },
                    {
                        "uid": 103,
                        "username": "Harden",
                        "authority": "user",
                        "email": "lcan@gmail.com",
                        "status": "frozen"
                    },
                    {
                        "uid": 104,
                        "username": "kobe",
                        "authority": "user",
                        "email": "kobe@gmail.com",
                        "status": "frozen"
                    },
                    {
                        "uid": 105,
                        "username": "Har",
                        "authority": "user",
                        "email": "lcan@gmail.com",
                        "status": "frozen"
                    },
                    {
                        "uid": 106,
                        "username": "XXXXG",
                        "authority": "user",
                        "email": "XXXG@gmail.com",
                        "status": "normal"
                    },
                    {
                        "uid": 107,
                        "username": "XXXA",
                        "authority": "user",
                        "email": "lcan@gmail.com",
                        "status": "normal"
                    },
                    {
                        "uid": 108,
                        "username": "VAVA",
                        "authority": "user",
                        "email": "lcan@gmail.com",
                        "status": "normal"
                    },
                    {
                        "uid": 109,
                        "username": "Paul",
                        "authority": "user",
                        "email": "lcan@gmail.com",
                        "status": "normal"
                    },
                    {
                        "uid": 110,
                        "username": "Dawe",
                        "authority": "user",
                        "email": "lcan@gmail.com",
                        "status": "normal"
                    }
                ]
            }  

