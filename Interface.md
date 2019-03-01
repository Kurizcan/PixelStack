# 接口说明

## 用户

### 获取用户信息


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

    method:GET
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
