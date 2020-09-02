package com.walmart.fp.funcvalues;

@FunctionalInterface
interface Resolve {
    void resolve(Object response);
}

@FunctionalInterface
interface Reject {
    void reject(Object error);
}

class Login {
    public void validate(Resolve resolver, Reject rejection) {
        String userName = "admin";
        String password = "admin";
        if (userName.equals("admin") && password.equals("admin")) {
            resolver.resolve("Login Success");
        } else {
            rejection.reject("Login failed");
        }

    }
}

public class LoginValidation {
    public static void main(String[] args) {
        Login login = new Login();
        login.validate(response -> System.out.println(response), error -> System.out.println(error));
    }
}
