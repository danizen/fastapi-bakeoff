from django_filters import rest_framework as filters
from rest_framework.generics import (
    ListAPIView,
    RetrieveAPIView
)
from rest_framework.views import APIView
from rest_framework.response import Response

from .models import (
    ContactType,
    Contact
)
from .serializers import (
    ContactTypeSerializer,
    ContactSerializer
)


class VersionAPI(APIView):
    def get(self, request, format=None):
        return Response({'version': '0.0.1'})


class ContactTypeAPI(ListAPIView):
    queryset = ContactType.objects.all()
    serializer_class = ContactTypeSerializer


class ContactFilter(filters.FilterSet):
    starts = filters.CharFilter(
        field_name='last_name',
        lookup_expr='istartswith'
    )

    class Meta:
        model = Contact
        fields = ['starts']


class ContactListAPI(ListAPIView):
    queryset = Contact.objects.select_related('contact_type')
    serializer_class = ContactSerializer
    filter_backends = (filters.DjangoFilterBackend,)
    filterset_class = ContactFilter


class ContactDetailAPI(RetrieveAPIView):
    queryset = Contact.objects.select_related('contact_type')
    serializer_class = ContactSerializer
