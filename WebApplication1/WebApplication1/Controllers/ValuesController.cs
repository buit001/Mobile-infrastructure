using System;
using System.Collections.Generic;
using System.Linq;
using System.Web.Http;

namespace WebApplication1.Controllers
{
    public class ValuesController : ApiController
    {
        // Get api/values
        public List<stappen> Get()
        {
            try
            {
                using (var db = new Model1())
                {
                    var steps = db.stappen.ToList();
                    return steps;
                }
            }
            catch(Exception e)
            {
                return new List<stappen>();
            }
        }

        // Post api/values/5
        public List<stappen> Post()
        {
            using (var db = new Model1())
            {
                db.stappen.Add();
            }
        }
    }
}