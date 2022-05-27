package model;


/**
 * [Contacts Item Model]
 */
public class ContactsModel {
    private int contactsImageView;
    private String contactsProfileName;
    private String contactsDescription;

    public ContactsModel(int contactsImageView, String contactsProfileName, String contactsDescription) {
        this.contactsImageView = contactsImageView;
        this.contactsProfileName = contactsProfileName;
        this.contactsDescription = contactsDescription;
    }

    public int getContactsImageView() {
        return contactsImageView;
    }

    public void setContactsImageView(int contactsImageView) {
        this.contactsImageView = contactsImageView;
    }

    public String getContactsProfileName() {
        return contactsProfileName;
    }

    public void setContactsProfileName(String contactsProfileName) {
        this.contactsProfileName = contactsProfileName;
    }

    public String getContactsDescription() {
        return contactsDescription;
    }

    public void setContactsDescription(String contactsDescription) {
        this.contactsDescription = contactsDescription;
    }
}
