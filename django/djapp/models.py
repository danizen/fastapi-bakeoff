from django.db import models


class ContactType(models.Model):
    type_id = models.AutoField(primary_key=True)
    type_name = models.CharField(max_length=30)

    class Meta:
        db_table = 'contact_types'
        managed = False


class Contact(models.Model):
    contact_id = models.AutoField(primary_key=True)
    first_name = models.CharField(max_length=100)
    last_name = models.CharField(max_length=100)

    contact_type = models.ForeignKey(
        ContactType,
        on_delete=models.SET_NULL,
        db_column='type_id',
        related_name='contacts',
        null=True,
    )

    class Meta:
        db_table = 'contacts'
        managed = False


class ContactPhone(models.Model):
    phone_id = models.AutoField(primary_key=True)
    phone_number = models.CharField(max_length=30)
    contact = models.ForeignKey(
        Contact,
        on_delete=models.CASCADE,
        related_name='phones'
    )

    class Meta:
        db_table = 'contact_phones'
        managed = False


class ContactEmail(models.Model):
    email_id = models.AutoField(primary_key=True)
    email_address = models.CharField(max_length=256)
    contact = models.ForeignKey(
        Contact,
        on_delete=models.CASCADE,
        related_name='emails'
    )

    class Meta:
        db_table = 'contact_emails'
        managed = False
