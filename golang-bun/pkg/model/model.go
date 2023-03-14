package database

import (
	"github.com/uptrace/bun"
)

type ContactTypeModel struct {
	bun.BaseModel `bun:"table:contact_types,alias:ct"`
	ID            uint   `bun:"type_id,pk"`
	Name          string `bun:"type_name"`
}

type ContactModel struct {
	bun.BaseModel `bun:"table:contacts,alias:ct"`
	ID            uint   `bun:"contact_id,pk,autoincrement"`
	FirstName     string `bun:"first_name"`
	LastName      string `bun:"last_name"`
}

type PhoneNumberModel struct {
	bun.BaseModel `bun:"table:contact_phones,alias:p"`
	ContactID     uint   `bun:"contact_id"`
	PhoneNumber   string `bun:"phone_number"`
}

type EmailModel struct {
	bun.BaseModel `bun:"table:contact_emails,alias:e"`
	ContactID     uint   `bun:"contact_id"`
	Email         string `bun:"email_address"`
}
