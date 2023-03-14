package schema

import "math/rand"

func (c *ContactType) Fake() {
	c.ID = 2
	c.Name = "Coworkers"
}

func (c *Contact) Fake() {
	c.ID = uint(rand.Uint32())
	c.FirstName = "Sonya"
	c.LastName = "Liyung"
	c.ContactType.Fake()
	c.PhoneNumber = []PhoneNumber{
		"222-182-1828",
		"222-129-7173",
	}
	c.Email = []Email{
		"sonya.liyung@capitalone.com",
		"sonayali@gmail.com",
	}
}

func (c *ContactTypesResponse) Fake() {
	c.Count = 3
	c.Results = []ContactType{
		{ID: 0, Name: "Relatives"},
		{ID: 1, Name: "Friends"},
		{ID: 2, Name: "Coworkers"},
	}
}

func (c *ContactsResponse) Fake() {
	c.Count = 2
	c.Results = make([]Contact, 2)
	c.Results[0].Fake()
	c.Results[1].Fake()
}
