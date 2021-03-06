﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using WebDatabase;
using RestfulWebAPI.Models;
using System.Web.Http.Description;

namespace RestfulWebAPI.Controllers
{
    [RoutePrefix("api/Stappen")]
    public class ValuesController : ApiController
    {

        [HttpGet, Route("GetValues")]
        // GET api/GetValues
        public dynamic GetValues()
        {
            try {
                return GetStappenHistory();
            }
            catch(Exception e)
            {
                return e.Message;
            }

        }

        [HttpPost, Route("PostValues"), ResponseType(typeof(AddStepsMessage))]
        // POST api/PostValues
        public dynamic Post([FromBody] StappenModel stappenModel)
        {
            if (ModelState.IsValid)
            {
                return Ok(AddSteps(stappenModel.AantalStappen, stappenModel.Dag));
            }
            else
            {
                return BadRequest();
            }
        }


        public static StappenListModel GetStappenHistory()
        {
                using (var db = new Model1())
                {
                    StappenListModel StappenModel = new StappenListModel();
                    List<StappenModel> myStappen = new List<StappenModel>();
                var query = from s in db.stappen
                            select s;
                foreach (var item in query)
                    {
                        myStappen.Add(new StappenModel()
                        {
                            AantalStappen = item.aantalStappen,
                            Dag = item.dag

                        });
                    }
                    StappenModel.stappenModel = myStappen;
                    return StappenModel;
                }
            }

        public static AddStepsMessage AddSteps(string stappen, string day)
        {
            try
            {
                using (var db = new Model1())
                {
                    stappen stappenModel = new stappen()
                    {
                        aantalStappen = stappen,
                        dag = day
                    };

                    db.stappen.Add(stappenModel);
                    db.SaveChanges();

                    return new AddStepsMessage()
                    {
                        Succesfull = true
                    };
                }
            }
            catch (Exception e)
            {
                return new AddStepsMessage()
                {
                    Succesfull = false,
                    Message = e.Message + " " + e.InnerException.ToString()
                };
            }
        }
    }
}
