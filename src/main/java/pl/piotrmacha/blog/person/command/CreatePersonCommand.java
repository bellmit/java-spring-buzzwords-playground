package pl.piotrmacha.blog.person.command;

final public class CreatePersonCommand {
    public String name;
    public String email;
    public String password;

    public CreatePersonCommand(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
}
