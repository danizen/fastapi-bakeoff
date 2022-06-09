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


# This is an intentionally naively recursive
# implementation that ignores the basics of
# dynamic programming. We want it to slow down
# arbitrarily through busy work.
def fib(n: int) -> int:
    if n < 0:
        raise ValueError('should be non-negative')
    elif n < 2:
        return 1
    else:
        return fib(n-1) + fib(n-2)


class FibonacciAPI(APIView):
    def get(self, request, format=None, number=None):
        try:
            if not (0 <= number < 35):
                raise ValueError('should be between 1 and 34')
            result = fib(number)
            return Response({'result': result})
        except ValueError as e:
            return Response({
                'detail': str(e)
            }, status=400)


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
