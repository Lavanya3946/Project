<%@page isELIgnored="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>All Users</title>
    <!-- Add Font Awesome Library -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #f8f9fa;
        }
        .container {
            margin-top: 5rem;
            text-align: center;
        }
        h1 {
            margin-bottom: 2rem;
            color: #007bff;
        }
        .btn-custom {
            margin: 0.5rem;
            padding: 0.75rem 1.5rem;
            background-color: #17a2b8;
            border: none;
            background-color: pink;
        }
        .btn-custom a {
            color: white;
            text-decoration: none;
        }
        .btn-custom:hover {
            background-color: red;
        }
        .navbar-custom {
            background-color: black;
        }
        .navbar-custom .navbar-brand,
        .navbar-custom .nav-link {
            color: white;
        }
        .nav-link:hover {
            background-color: red;
        }
        .navbar-nav {
            text-align: center;
        }
    </style>
</head>
<body>
  <!-- Navbar -->
    <nav class="navbar navbar-expand-lg navbar-custom">
        <div class="container-fluid">
            <a class="navbar-brand" href="#">Pg Portal</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav">
                    <li class="nav-item">
                        <a class="nav-link" href="index.jsp">Home</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="signin.jsp">SignIn</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="fetchUsers">Update Page</a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>

    <div class="container mt-5">
        <h1 style="text-align:center;">Registered data</h1>

        <table class="table table-striped">
            <thead>
                <tr>
                    <th>Id</th>
                    <th>Full Name</th>
                    <th>Email</th>
                    <th>Age</th>
                    <th>PhoneNumber</th>
                    <th>Location</th>

                </tr>
            </thead>
            <tbody>
                <c:set var="users" value="${user}"/>
                    <tr>
                        <td>${users.id}</td>
                        <td>${users.fullName}</td>
                        <td>${users.email}</td>
                        <td>${users.age}</td>
                        <td>${users.phoneNumber}</td>
                        <td>${users.location}</td>

                    </tr>

            </tbody>
        </table>
                        <button  type="submit"class="btn btn-primary"> Update data</a></button>


    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
