<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>사용자 트래킹</title>
</head>

<body>
    <h4>HackDay Tracking User!!</h4>
    <h3>정교한 분석을 위해 csv 파일을 만들어보세요^^</h3>
    <form name="makingFileForm" method="GET" action="http://localhost:8080/trackingUser/makingFile" accept-charset="utf-8">
        <input type = "submit" value = "csv 파일 만들기" />
    </form>
</body>
</html>