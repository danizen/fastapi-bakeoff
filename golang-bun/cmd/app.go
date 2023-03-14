package main

import (
	"net/http"

	"github.com/danizen/fastapi-bakeoff/golang-bun/pkg/schema"
	"github.com/gin-gonic/gin"
)

const VERSION = "0.1"

func main() {
	app := gin.Default()
	// TODO: trusted proxies should come from config
	app.SetTrustedProxies(nil)
	app.GET("/version/", func(c *gin.Context) {
		var version = schema.Version{Version: VERSION}
		c.JSON(200, version)
	})
	app.GET("/fibonacci/:number/", func(c *gin.Context) {
		var params schema.FibonacciParams
		if err := c.ShouldBindUri(&params); err != nil {
			schema.EncodeError(c, err)
			return
		}
		var response = schema.FibonacciResponse{Result: params.Number}
		c.JSON(http.StatusOK, response)
	})
	app.GET("/types/", func(c *gin.Context) {
		var response = schema.ContactTypesResponse{
			Count: 3, Results: []schema.ContactType{
				{ID: 0, Name: "Relatives"},
				{ID: 1, Name: "Friends"},
				{ID: 2, Name: "Coworkers"},
			},
		}
		c.JSON(http.StatusOK, response)
	})
	app.GET("/contacts/:contact_id/", func(c *gin.Context) {
		var params schema.ContactParams
		if err := c.ShouldBindUri(&params); err != nil {
			schema.EncodeError(c, err)
			return
		}
		var response = schema.Contact{
			ID:        params.ContactID,
			FirstName: "Sonya",
			LastName:  "Liyung",
			ContactType: schema.ContactType{
				ID: 2, Name: "Coworkers",
			},
			PhoneNumber: []schema.PhoneNumber{
				"222-182-1828",
				"222-129-7173",
			},
			Email: []schema.Email{
				"sonya.liyung@capitalone.com",
				"sonayali@gmail.com",
			},
		}
		c.JSON(http.StatusOK, response)
	})
	app.GET("/contacts/", func(c *gin.Context) {
		var params schema.ContactsParams
		if err := c.ShouldBindQuery(&params); err != nil {
			schema.EncodeError(c, err)
			return
		}
		var response = schema.ContactsResponse{
			Count: 2,
			Results: []schema.Contact{
				{
					ID:        1,
					FirstName: "Sonya",
					LastName:  "Liyung",
					ContactType: schema.ContactType{
						ID: 2, Name: "Coworkers",
					},
				},
				{
					ID:        3,
					FirstName: "Dan",
					LastName:  "Davis",
					ContactType: schema.ContactType{
						ID: 2, Name: "Coworkers",
					},
				},
			},
		}
		c.JSON(http.StatusOK, response)
	})
	app.Run()
}
