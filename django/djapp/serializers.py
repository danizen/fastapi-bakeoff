from rest_framework import serializers as s

from .models import (
    ContactType,
    Contact,
)


class VersionSerializer(s.Serializer):
    version = s.CharField()


class ContactTypeSerializer(s.ModelSerializer):
    class Meta:
        model = ContactType
        fields = ['type_id', 'type_name']


class ContactSerializer(s.ModelSerializer):
    contact_type = ContactTypeSerializer(read_only=True)
    phones = s.StringRelatedField(many=True)
    emails = s.StringRelatedField(many=True)

    class Meta:
        model = Contact
        fields = [
            'contact_id',
            'first_name',
            'last_name',
            'contact_type',
            'phones',
            'emails',
        ]
