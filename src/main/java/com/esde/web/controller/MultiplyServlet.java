package com.esde.web.controller;

//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/multiply")
public class MultiplyServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String numberStr = request.getParameter("number");
        if (numberStr != null && !numberStr.isEmpty()) {
            try {
                int number = Integer.parseInt(numberStr);
                int result = number * 2;

                response.setContentType("text/plain");
                response.getWriter().write("Результат умножения числа " + number + " на 2: " + result);
            } catch (NumberFormatException e) {
                response.getWriter().write("Ошибка: Некорректное число.");
            }
        } else {
            response.getWriter().write("Ошибка: Параметр 'number' отсутствует или пустой.");
        }
    }
}