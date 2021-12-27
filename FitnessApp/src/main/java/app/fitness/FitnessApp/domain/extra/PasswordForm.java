package app.fitness.FitnessApp.domain.extra;

import javax.validation.constraints.Size;

public class PasswordForm {

    private String oldPassword;
    @Size(min=8, max=24, message = "Hasło zbyt krótkie")
    private String newPassword;

    public PasswordForm() {
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
