package com.example.Employee_Directory.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    public int userId;
    public int id;
    public String title;
    public String body;
}
