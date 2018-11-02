package pl.piotrmacha.blog.person.query;

import java.util.UUID;

final public class FetchPersonById {
    private UUID personId;

    public FetchPersonById(UUID personId) {
        this.personId = personId;
    }

    public UUID personId() {
        return personId;
    }
}
