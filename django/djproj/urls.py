"""asyncdj URL Configuration

The `urlpatterns` list routes URLs to views. For more information please see:
    https://docs.djangoproject.com/en/3.2/topics/http/urls/
Examples:
Function views
    1. Add an import:  from my_app import views
    2. Add a URL to urlpatterns:  path('', views.home, name='home')
Class-based views
    1. Add an import:  from other_app.views import Home
    2. Add a URL to urlpatterns:  path('', Home.as_view(), name='home')
Including another URLconf
    1. Import the include() function: from django.urls import include, path
    2. Add a URL to urlpatterns:  path('blog/', include('blog.urls'))
"""
from django.urls import path

from djapp.views import (
    VersionAPI,
    FibonacciAPI,
    ContactTypeAPI,
    ContactListAPI,
    ContactDetailAPI,
)

urlpatterns = [
    path('version/', VersionAPI.as_view()),
    path('fibonacci/<int:number>/', FibonacciAPI.as_view()),
    path('types/',  ContactTypeAPI.as_view()),
    path('contacts/', ContactListAPI.as_view()),
    path('contacts/<int:pk>/', ContactDetailAPI.as_view())
]
