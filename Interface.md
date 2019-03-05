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

### 根据用户名查找返回用户 id

    method:GET
    http://localhost:8080/user/getUid?username=VAVAa

    return:
            {
                "message": "查无此人",
                "status": 500
            }

            {
                "uid": 108,
                "status": 200
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

### 删除图片

    method:POST
    http://localhost:8080/user/deleteImage

    params:
        iid:109

    {
        "message": "删除失败",
        "status": 500
    }

    {
        "message": "删除成功",
        "status": 200
    }    

### 添加标签

    method:POST
    http://localhost:8080/user/addTagsandTitle

    params:
        {
            "pids":[116, 115],
            "tags":["大骚伟", "孙燕姿"],
        }

    return 
        {
            "tagIsAdd": "Yes",
            "status": 200
        }

### 关注用户

    method:POST
    http://localhost:8080/user/isFollow

    params:
        isFollow:false
        uid:108
        fid:104

    return:
            {
                "isFollow": false,
                "status": 200
            }    

### 获取用户关注列表

    method:GET
    http://localhost:8080/user/getFollowers

    params:
        uid:104

    return:
            {
                "followers": [
                    {
                        "fid": 108,
                        "introduction": "fuckkkkk",
                        "username": "kobe"
                    },
                    {
                        "fid": 105,
                        "introduction": null,
                        "username": "Har"
                    }
                ],
                "status": 200
            }

            {
                "followers": []
                "status": 200
            }            

### 获取粉丝列表

    method:GET
    http://localhost:8080/user/getFans

    params:
        uid:104

    return:
            {
                "fans": [
                    {
                        "uid": 108,
                        "introduction": null,
                        "username": "VAVA"
                    }
                ],
                "status": 200
            }            

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
                "userList": [
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


## 图片

### 显示图片详细信息

    method:GET
        http://localhost:8080/image/getImageDetails


    params:
        iid:153

    return:
            {
                "message": "图片不存在",
                "status": 500
            }    
            
            {
                "isThumb": false,
                "isFollow": false,
                "star": 3,
                "isStar": false,
                "upload": "Sun Mar 03 2019",
                "thumb": 3,
                "author": "Paul",
                "count": 0,
                "title": "wnba",
                "url": "C:\\Users\\asus\\Desktop\\users\\kobe\\2019-03-03\\original\\IMG_20170223_170105.jpg",
                "tags": [
                    "大骚伟",
                    "孙燕姿"
                ]
            }               

### 显示该图片的所有评论

    method:GET
    http://localhost:8080/Comment/getCommentsByiid

    params:
        iid:153

    return:
            {
                "comments": [
                    {
                        "cdate": "2019-03-04",
                        "content": "great",
                        "username": "Curry"
                    },
                    {
                        "cdate": "2019-03-04",
                        "content": "wonderful",
                        "username": "Dawe"
                    }
                ],
                "status": 200
            }

            当该图无评论时
            {
                "comments": [],
                "status": 200
            }

### 首页图片显示

    method:GET
    http://localhost:8080/image/getImageList

    params:
        pageNo:2
        pageSize:30
        若不带参数，默认pageSize = 60， pageNo = 1

    return:
            {
                "total": 64,
                "curPage": 2,
                "lastPage": 3,
                "prePage": 1,
                "nextPage": 3,
                "imageList": [
                    {
                        "star": 0,
                        "iid": 144,
                        "thumb": 0,
                        "count": 0,
                        "url": "C:\\Users\\asus\\Desktop\\users\\kobe\\2019-03-03\\small\\IMG_20170224_190901.jpg"
                    },
                    {
                        "star": 0,
                        "iid": 145,
                        "thumb": 0,
                        "count": 0,
                        "url": "C:\\Users\\asus\\Desktop\\users\\kobe\\2019-03-03\\small\\IMG_20170222_161223.jpg"
                    },
                    {
                        "star": 0,
                        "iid": 146,
                        "thumb": 0,
                        "count": 0,
                        "url": "C:\\Users\\asus\\Desktop\\users\\kobe\\2019-03-03\\small\\IMG_20170224_154309.jpg"
                    },
                    {
                        "star": 0,
                        "iid": 147,
                        "thumb": 0,
                        "count": 0,
                        "url": "C:\\Users\\asus\\Desktop\\users\\kobe\\2019-03-03\\small\\IMG_20170223_170105.jpg"
                    },
                    {
                        "star": 0,
                        "iid": 148,
                        "thumb": 0,
                        "count": 0,
                        "url": "C:\\Users\\asus\\Desktop\\users\\kobe\\2019-03-03\\small\\IMG_20170223_171111.jpg"
                    },
                    {
                        "star": 0,
                        "iid": 149,
                        "thumb": 0,
                        "count": 0,
                        "url": "C:\\Users\\asus\\Desktop\\users\\kobe\\2019-03-03\\small\\IMG_20170224_190901.jpg"
                    },
                    {
                        "star": 0,
                        "iid": 150,
                        "thumb": 1,
                        "count": 0,
                        "url": "C:\\Users\\asus\\Desktop\\users\\kobe\\2019-03-03\\small\\IMG_20170222_161223.jpg"
                    },
                    {
                        "star": 0,
                        "iid": 151,
                        "thumb": 1,
                        "count": 0,
                        "url": "C:\\Users\\asus\\Desktop\\users\\kobe\\2019-03-03\\small\\IMG_20170224_154309.jpg"
                    },
                    {
                        "star": 3,
                        "iid": 152,
                        "thumb": 4,
                        "count": 0,
                        "url": "C:\\Users\\asus\\Desktop\\users\\kobe\\2019-03-03\\small\\IMG_20170223_170105.jpg"
                    },
                    {
                        "star": 0,
                        "iid": 153,
                        "thumb": 0,
                        "count": 0,
                        "url": "C:\\Users\\asus\\Desktop\\users\\kobe\\2019-03-03\\small\\IMG_20170223_171111.jpg"
                    }
                ]
            }

### 用户首页显示

    method:GET
    http://localhost:8080/image/getImageListByUid

    params:
        uid:104

    return:
            {
                "imageList": [
                    {
                        "star": 0,
                        "iid": 100,
                        "thumb": 0,
                        "count": 0,
                        "url": "C:\\Users\\asus\\Desktop\\users\\kobe\\2019-03-03\\bigIMG_20170222_125814.jpg"
                    },
                    {
                        "star": 0,
                        "iid": 102,
                        "thumb": 0,
                        "count": 0,
                        "url": "C:\\Users\\asus\\Desktop\\users\\kobe\\2019-03-03\\bigIMG_20170222_195652.jpg"
                    }        
                ]
            }               

### 添加评论

    method:Post
    http://localhost:8080/Comment/add

    params:
        iid:152
        uid:106
        content:cool   

    return:
            {
                "message": "评论成功",
                "status": 200
            }

            失败将发生 500 错误

### 收藏

    method:Get
    http://localhost:8080/image/isStar

    params:
        uid:108
        iid:152
        isStar:false（取消收藏）true（收藏）

    return:
            {
                "isStar": false,
                "status": 200
            }   

        500 错误

### 点赞

    method:Get
    http://localhost:8080/image/isThumb

    params:
        uid:108
        iid:152
        isThumb:false    

    return:
            {
                "isThumb": false,
                "status": 200
            }        