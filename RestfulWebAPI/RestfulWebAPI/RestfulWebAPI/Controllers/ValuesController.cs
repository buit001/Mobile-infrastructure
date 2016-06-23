using System;
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
        // GET api/values
        public IEnumerable<string> Get()
        {
            return new string[] { "value1", "value2" };
        }

        [HttpGet, Route("GetValues")]
        public dynamic GetValues()
        {
            return GetStappenHistory();
        }

        [HttpPost, Route("PostValues"), ResponseType(typeof(AddStepsMessage))]
        // POST api/values
        public dynamic Post([FromBody] StappenModel stappenModel)
        {
            if (ModelState.IsValid)
            {
                return Ok(AddSteps(stappenModel.IdStappen, stappenModel.AantalStappen, stappenModel.Dag));
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
                        IdStappen = item.idStappen,
                        AantalStappen = item.aantalStappen,
                        Dag = item.dag

                    });
                }
                StappenModel.stappenModel = myStappen;
                return StappenModel;
            }
        }

        public static AddStepsMessage AddSteps(int id, string stappen, string day)
        {
            try
            {
                using (var db = new Model1())
                {
                    var mySteps = db.stappen.FirstOrDefault(i => i.idStappen == id);
                    db.stappen.Add(new stappen()
                    {
                        aantalStappen = stappen,
                        dag = day                        
                    });
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
                    Message = e.Message
                };
            }
        }
    }
}
