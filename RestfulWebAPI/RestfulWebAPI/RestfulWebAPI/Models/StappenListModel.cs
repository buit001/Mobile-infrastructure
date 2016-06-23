using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace RestfulWebAPI.Models
{
    public class StappenListModel
    {
        public IEnumerable<StappenModel> stappenModel { get; set; }
    }
}