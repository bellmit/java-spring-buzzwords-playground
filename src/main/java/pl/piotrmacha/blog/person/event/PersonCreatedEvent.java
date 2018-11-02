package pl.piotrmacha.blog.person.event;

import pl.piotrmacha.blog.person.domain.Person;

final public class PersonCreatedEvent {
    private Person person;

    public PersonCreatedEvent(Person person) {
        this.person = person;
    }

    public Person person() {
        return person;
    }
}
