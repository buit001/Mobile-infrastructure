using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;

namespace RestfulWebAPI.Controllers
{
    [RoutePrefix("values")]
    //[Authorize]
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
            return true;
        }

        [HttpPost, Route("PostValues")]
        // POST api/values
        public dynamic Post([FromBody]string value)
        {
            if (ModelState.IsValid)
            {
                return true;
            }
            else
            {
                return false;
            }
        }
    }
}
