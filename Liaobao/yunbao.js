 var name= request.query.name;
  var row2={code:0,msg:'注册成功'};
  var password= request.query.password;
  if(name == null || name.length == 0) {
       row2={code:1,msg:'用户名不能为空'}
     response.send(row2);
    return;
  }
	 if(password == null || password.length == 0) {
	     row2={code:1,msg:'密码不能为空'}
      response.send(row2);
    return;
  }
  var crypto = modules.oCrypto;
  var db = modules.oData;
  db.find({
    "table": "_User",
    "keys": "objectId",
    "where": { "mobilePhoneNumber": name },
    "count": 1
  }, function (err, data) {//回调函数
    resultObject = JSON.parse(data);
    count = resultObject.count;
    if (count > 0) {
         row2={code:1,msg:'该手机号已经被注册过，请更换手机号'}
      response.send(row2);
    } else {//可注册
      db.insert({
        "table": "_User",
        "data": {
          "mobilePhoneNumber": name, "username": name, "password":password}//需要更新的数据，格式为JSON
        }, function(err, data) {//回调函数
        if(data!=null){
            if(JSON.parse(data).objectId!=null){
              row2={code:0,msg:'注册成功'};
            }else{
               row2={code:1,msg:'注册失败'}
            }
        }else{
           row2={code:1,msg:'注册失败'}
        }
          response.send(row2);
        });
    }
  });