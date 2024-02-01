package com.driver;

public class Email {

    private String emailId;
    private String password;

    public Email(String emailId){
        this.emailId = emailId;
        this.password = "Accio@123";
    }
    public String getEmailId() {
        return emailId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password){this.password=password;}

    public void changePassword(String oldPassword, String newPassword){
        //Change password only if the oldPassword is equal to current password and the new password meets all of the following:
        // 1. It contains at least 8 characters
        // 2. It contains at least one uppercase letter
        // 3. It contains at least one lowercase letter
        // 4. It contains at least one digit
        // 5. It contains at least one special character. Any character apart from alphabets and digits is a special character
        if(oldPassword.equals(getPassword()) && checkPassword(newPassword)) setPassword(newPassword);
    }
    boolean checkPassword(String password){
        if(password.length()<8) return false;
        int countLowerCaseLetter = 0 ,countUpperCaseLetter = 0 , countDigits = 0 , countSpecialCharacter = 0;
        for(char ch : password.toCharArray()){
            if(ch>='a' && ch<='z') countLowerCaseLetter++;
            else if(ch>='A' && ch<='Z') countUpperCaseLetter++;
            else if(ch>='0' && ch<='9') countDigits++;
            else countSpecialCharacter++;
        }
        if(countLowerCaseLetter==0) return false;
        else if(countUpperCaseLetter==0) return false;
        else if(countDigits==0) return false;
        else if(countSpecialCharacter==0) return false;

        return true;
    }
}
