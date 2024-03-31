package model;

public class User {
    private Long sequenceId;
    private String loginId;
    private String password;
    private String name;
    private String email;

    public User(String loginId, String password, String name, String email) {
        this.loginId = loginId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public boolean hasSamePassword(String password) {
        return this.password.equals(password);
    }

    public String getLoginId() {
        return loginId;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Long getSequenceId() {
        return sequenceId;
    }

    public void setSequenceId(Long sequenceId) {
        this.sequenceId = sequenceId;
    }

    @Override
    public String toString() {
        return "User [loginId=" + loginId + ", password=" + password + ", name=" + name + ", email=" + email + "]";
    }
}
