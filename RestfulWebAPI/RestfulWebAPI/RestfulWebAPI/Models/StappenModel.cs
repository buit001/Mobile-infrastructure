using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace RestfulWebAPI.Models
{
    public class StappenModel
    {
        /// <summary>
        /// ID of transaction
        /// </summary>
        public int IdStappen { get; set; }

        /// <summary>
        /// Value of transaction
        /// </summary>
        public String AantalStappen { get; set; }

        public String Dag { get; set; }

    }
}