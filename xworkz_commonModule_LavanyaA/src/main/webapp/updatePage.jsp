<%@page isELIgnored="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Update</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@400;700&display=swap" rel="stylesheet">
    <style>
      body {
                 background: url('https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRxIhYuGvGgPIJ7vzgz8ptYNPCreSvd4gJfZQ&s') no-repeat center center fixed;
                 background-size: cover;
                 font-family: 'Montserrat', sans-serif;
                 color: black;
                 height: 100vh;
                overflow:auto;
             }
             .container {
                 margin-top: 1rem;
                 max-width: 900px;
                 height: 500px;
                 background: rgba(255, 255, 255, 0.8);
                 padding: 2rem;
                 border-radius: 10px;
                 box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
             }
             h1 {
                 margin-bottom: 2rem;
                 color: #007bff;
                 text-shadow: 2px 2px 4px #000;
                 text-align: center;
             }
             .form-container {
                 background: violet;
                 padding: 2rem;

                 border-radius: 10px;
                 box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
             }
             .btn-custom {
                 margin: 0.5rem 10;
                 padding: 0.75rem 1.5rem;
                 background-color: #17a2b8;
                 border: none;
                 color: white;
                 width: 30%;
                 transition: background-color 0.3s;
             }

        .form-control:focus {
            border-color: #007bff;
            box-shadow: 0 0 10px rgba(0, 123, 255, 0.5);
        }
        .navbar-custom {
            background-color: rgba(0, 0, 0, 0.8);
        }
        .navbar-custom .navbar-brand,
        .navbar-custom .nav-link {
            color: white;
            text-align:center;
        }
        .nav-link:hover {
            background-color: #007bff;
            color: white;
        }
        .nav-item{
        text-align:right;
        }
        .error-message {
            color: red;
            font-size: 0.9rem;
        }
    </style>
</head>
<body>
    <!-- Navbar -->
    <nav class="navbar navbar-expand-lg navbar-custom">
        <div class="container-fluid">


            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav ms-auto">
                    <li class="nav-item">
                        <a class="nav-link" href="index.jsp">Home</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="signin.jsp">SignIn</a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>



   <div class="container">
           <h1>Update Form</h1>

           <div class="form-container">
               <form action="updateUser" method="post">


                       Id:
                            <input type="hidden"   name="id" value="${user.id}" ><br>

                          Full Name:
                           <input type="text"  name="fullName" value="${user.fullName}" ><br>


                           Email:
                           <input type="hidden" id="email" name="email" value="${user.email}" ><br>

                            Gender:
                           <input type="hidden" id="male" name="gender" value="${user.gender}"><br>


                           Age:
                           <input type="number"  id="age" name="age" value="${user.age}" ><br>


                            Phone Number:
                              <input type="number"  id="phoneNumber" name="phoneNumber" value="${user.phoneNumber}" ><br>

                           Location:
                           <select id="location" name="location" >
                               <option value="">--Select Location--</option>
                               <option value="Bangalore" ${user.location == 'Bangalore' ? 'selected' : ''}>Bangalore</option>
                               <option value="Mysore" ${user.location == 'Mysore' ? 'selected' : ''}>Mysore</option>
                               <option value="Tumkur" ${user.location == 'Tumkur' ? 'selected' : ''}>Tumkur</option>
                               <option value="Hassan" ${user.location == 'Hassan' ? 'selected' : ''}>Hassan</option>
                               <option value="Mandya" ${user.location == 'Mandya' ? 'selected' : ''}>Mandya</option>
                           </select><br>

                               Password:
                              <input type="hidden"  id="password" name="password"  value="${user.password}"><br>

                                 <span>${msg}</span>


                   <button type="submit" class="btn btn-custom">Update</button>

               </form>
           </div>

       </div>

       <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
   </body>
   </html>