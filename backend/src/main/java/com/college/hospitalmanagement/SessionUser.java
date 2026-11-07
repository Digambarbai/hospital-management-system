package com.college.hospitalmanagement;

import java.io.Serializable;

public record SessionUser(Long id, String name, String email, String role) implements Serializable {
    private static final long serialVersionUID = 1L;
}
