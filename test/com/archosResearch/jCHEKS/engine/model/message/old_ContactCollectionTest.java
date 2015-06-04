package com.archosResearch.jCHEKS.engine.model.message;



/**
 *
 * @author Michael Roussel <rousselm4@gmail.com>
 *//*
public class ContactCollectionTest {

    @Test
    public void constructor_should_create_the_contact_collection() throws Exception {
        ContactCollection contactCollection = null;
        contactCollection = new ContactCollection();
        assertNotNull(contactCollection);
    }

    @Test(expected = ContactNotFoundException.class)
    public void findByName_should_throw_an_exception_when_we_search_and_no_contact_exist() throws ContactNotFoundException, NameOfContactAlreadyExistInContactsException {
        ContactCollection contactCollection = new ContactCollection();
        contactCollection.add(new Contact("Alice", new StubCommunicator()));
        contactCollection.findByName("Bob");
    }

    @Test(expected = ContactNotFoundException.class)
    public void findByName_should_throw_an_exception_when_we_search_for_a_unexisting_contact() throws ContactNotFoundException {
        ContactCollection contactCollection = new ContactCollection();
        contactCollection.findByName("Alice");
    }

    @Test
    public void findByName_return_the_good_contact_it_is_possible() throws ContactNotFoundException, NameOfContactAlreadyExistInContactsException {
        ContactCollection contactCollection = new ContactCollection();
        Contact contact = new Contact("Alice", new StubCommunicator());
        contactCollection.add(contact);
        String name = "Alice";
        Contact result = contactCollection.findByName(name);
        assertEquals(result, contact);
    }

    @Test(expected = NameOfContactAlreadyExistInContactsException.class)
    public void addContact_should_throw_an_exception() throws NameOfContactAlreadyExistInContactsException {
        ContactCollection contactCollection = new ContactCollection();
        Contact contact = new Contact("Alice", new StubCommunicator());
        contactCollection.add(contact);
        contactCollection.add(contact);
    }

    @Test
    public void addContact_should_add_a_contact_in_contact_collect() throws NameOfContactAlreadyExistInContactsException, ContactNotFoundException {
        ContactCollection contactCollection = new ContactCollection();
        Contact contact = new Contact("Alice", new StubCommunicator());
        contactCollection.add(new Contact("Bob", new StubCommunicator()));
        contactCollection.add(contact);
        Contact result = contactCollection.findByName("Alice");
        assertEquals(result, contact);
    }

}
*/