package schema

import (
	"net/http"

	"github.com/gin-gonic/gin"
)

type Version struct {
	Version string `json:"version"`
}

type FibonacciParams struct {
	Number uint `uri:"number" binding:"required,gte=0,lte=40"`
}

type FibonacciResponse struct {
	Result uint `json:"result"`
}

type ContactType struct {
	ID   uint   `json:"type_id"`
	Name string `json:"type_name"`
}

type PhoneNumber string

type Email string

type Contact struct {
	ID          uint          `json:"contact_id"`
	FirstName   string        `json:"first_name"`
	LastName    string        `json:"last_name"`
	ContactType ContactType   `json:"contact_type"`
	PhoneNumber []PhoneNumber `json:"phone_number"`
	Email       []Email       `json:"email"`
}

type ContactParams struct {
	ContactID uint `uri:"contact_id" binding:"required,gt=0"`
}

type ContactTypesResponse struct {
	Count   uint          `json:"count"`
	Results []ContactType `json:"results"`
}

type ContactsParams struct {
	Limit  uint   `json:"limit" default:"100" binding:"lte=2000"`
	Offset uint   `json:"offset"`
	Starts string `json:"starts"`
}

type ContactsResponse struct {
	Count   uint      `json:"count"`
	Results []Contact `json:"result"`
}

func EncodeError(c *gin.Context, err error) {
	c.JSON(http.StatusBadRequest, gin.H{
		"error":   "fubar",
		"message": "fubar",
	})
}
