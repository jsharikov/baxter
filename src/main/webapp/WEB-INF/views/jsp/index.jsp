<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title>Baxter project</title>
</head>
<body>
<table border="1">
    <tr>
        <td valign="top">
            <table border="1">
                <tr>
                    <td>Returns all entries</td>
                    <td>
                        <label for="limit">Show entries:</label>
                        <select id="limit" name="limit">
                            <option value="3">3</option>
                            <option value="5">5</option>
                            <option value="10">10</option>
                        </select>
                        <label for="page">Page:</label>
                        <select id="page" name="page">
                        </select>
                        <input id="offset" type="hidden" value="0"/>
                        <label for="orderBy">Order by:</label>
                        <select id="orderBy" name="orderBy">
                            <option value="id">id</option>
                            <option value="login">login</option>
                            <option value="password">password</option>
                        </select>
                        <label for="reverse">ASC | DESC:</label>
                        <select id="reverse" name="reverse">
                            <option value="false">ASC</option>
                            <option value="true">DESC</option>
                        </select>
                    </td>
                    <td><button id="getUsersBtn">Run</button></td>
                </tr>
                <tr>
                    <td>Return single entry</td>
                    <td>
                        <form id="readForm">
                            <label for="id">Id:</label>
                            <input id="userId" type="text"/>
                        </form>
                    </td>
                    <td><button id="getUserBtn">Run</button></td>
                </tr>
                <tr>
                    <td>New entry created</td>
                    <td>
                        <form id="addForm">
                            <label for="login">Login:</label>
                            <input id="login" type="text"/>
                            <label for="password">Password:</label>
                            <input id="password" type="text"/>
                        </form>
                    </td>
                    <td><button id="addUserBtn">Run</button></td>
                </tr>
                <tr>
                    <td>Update existing entry</td>
                    <td>
                        <form id="updateForm">
                            <label for="id">Id:</label>
                            <input id="id" type="text"/>
                            <label for="login">Login:</label>
                            <input id="login" type="text"/>
                            <label for="password">Password:</label>
                            <input id="password" type="text"/>
                        </form>
                    </td>
                    <td><button id="updateUserBtn">Run</button></td>
                </tr>
                <tr>
                    <td>Delete existing entry</td>
                    <td>
                        <form id="deleteForm">
                            <label for="id">Id:</label>
                            <input id="id" type="text"/>
                        </form>
                    </td>
                    <td><button id="deleteUserBtn">Run</button></td>
                </tr>
            </table>
        </td>
        <td>
            <table id="usersTable" border="1">
                <caption>User List</caption>
                <thead>
                <th>
                    Id
                </th>
                <th>
                    Login
                </th>
                <th>
                    Password
                </th>
                </thead>
                <tbody>
                </tbody>
            </table>
        </td>
    </tr>
</table>

<script src="http://code.jquery.com/jquery-2.1.4.min.js"></script>
<script>

    $( document ).ready(function() {
        renderPagination();
        $('#getUsersBtn').click(function() {
            $.ajax({
                        type: 'get',
                        url: 'http://localhost:8082/user?limit=' + $('#limit').val() + '&offset=' + $('#offset').val()  + '&orderBy=' + $('#orderBy').val() + '&reverse=' + $('#reverse').val(),
                        contentType: "application/json",
                        dataType: 'json',
                        success: function (data) {
                            var items = [];

                            $.each(data, function(key, user){
                                items.push('<tr><td>' + user.id + '</td><td>' + user.login + '</td><td>' + user.password + '</td></tr>');
                            });

                            $('#usersTable tbody').html(items);

                        },
                        error: function(jqXHR, textStatus, errorThrown) {
                            console.log(textStatus, errorThrown);
                        }
                    }
            )
        });

        $('#getUserBtn').click(function() {
            $.ajax({
                        type: 'get',
                        url: 'http://localhost:8082/user/' + $('#readForm #userId').val(),
                        contentType: "application/json",
                        dataType: 'json',
                        success: function (user) {
                            $('#usersTable tbody').html('<tr><td>' + user.id + '</td><td>' + user.login + '</td><td>' + user.password + '</td></tr>');
                        },
                        error: function(jqXHR, textStatus, errorThrown) {
                            console.log(textStatus, errorThrown);
                        }
                    }
            )
        });

        $('#addUserBtn').click(function() {
            var login = $('#addForm #login').val();
            var psw = $('#addForm #password').val();
            $.ajax({
                        type: 'post',
                        url: 'http://localhost:8082/user/',
                        data: JSON.stringify({login: login, password: psw}),
                        contentType: "application/json",
                        dataType: 'json'
                    }
            )
        });

        $('#updateUserBtn').click(function() {
            var id = $('#updateForm #id').val();
            var login = $('#updateForm #login').val();
            var psw = $('#updateForm #password').val();
            $.ajax({
                        type: 'post',
                        url: 'http://localhost:8082/user/' + id,
                        data: JSON.stringify({login: login, password: psw}),
                        contentType: "application/json",
                        dataType: 'json'
                    }
            )
        });

        $('#deleteUserBtn').click(function() {
            var id = $('#deleteForm #id').val();
            $.ajax({
                        type: 'DELETE',
                        url: 'http://localhost:8082/user/' + id,
                        contentType: "application/json",
                        dataType: 'json'
                    }
            )
        });

        $('#page').change(function() {
            var page = $('#page').val();
            $('#offset').val((page - 1) * $('#limit').val());
        });

        $('#limit').change(function() {
            renderPagination();
        });
    });

    function renderPagination() {
        $.ajax({
                    type: 'get',
                    url: 'http://localhost:8082/user/count?limit=100&offset=0',
                    dataType: 'json',
                    success: function (recordsTotal) {
                        var pages = Math.ceil(recordsTotal / $('#limit').val());
                        $('#page').html('');
                        for (var i = 0;i < pages; i++) {
                            $('#page').append('<option value=' + (i + 1) + '>' + (i + 1) + '</option>');
                        }
                        $('#offset').val('0');

                    }
                }
        );
    }
</script>
</body>
</html>