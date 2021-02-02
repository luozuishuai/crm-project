<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <style type="text/css">
        h3{
            text-align: center;
        }
        table{
            text-align: center;
            margin: 0 auto;
            width: 1200px;
            border-collapse: collapse;
        }
        th,td{
            line-height: 35px;
            border: 1px solid #ccc;
        }
    </style>
</head>
<body>
<h3>hello,${adminName}</h3>
<table>
    <tr>
        <th>id</th>
        <th>后台账户</th>
        <th>初始密码</th>
        <th>昵称</th>
        <th>电话</th>
        <th>部门</th>
        <th>身份证号</th>
        <th>薪资</th>
    </tr>
    <tr>
        <td>${id}</td>
        <td>${adminAccount}</td>
        <td>123456</td>
        <td>${nickName}</td>
        <td>${adminPhone}</td>
        <td>${adminDept}</td>
        <td>${adminCode}</td>
        <td>${adminSalary}</td>
    </tr>
</table>

</body>
</html>