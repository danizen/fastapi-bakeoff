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
		var response = schema.ContactTypesResponse{}
		response.Fake()
		c.JSON(http.StatusOK, response)
	})
	app.GET("/contacts/:contact_id/", func(c *gin.Context) {
		var params schema.ContactParams
		if err := c.ShouldBindUri(&params); err != nil {
			schema.EncodeError(c, err)
			return
		}
		var response = schema.Contact{}
		response.Fake()
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
