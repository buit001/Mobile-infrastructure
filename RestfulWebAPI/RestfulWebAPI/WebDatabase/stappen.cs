namespace WebDatabase
{
    using System;
    using System.Collections.Generic;
    using System.ComponentModel.DataAnnotations;
    using System.ComponentModel.DataAnnotations.Schema;
    using System.Data.Entity.Spatial;

    [Table("stappentellerdatabase.stappen")]
    public partial class stappen
    {
        [Key]
        [DatabaseGenerated(DatabaseGeneratedOption.None)]
        public int idStappen { get; set; }

        [StringLength(45)]
        public string aantalStappen { get; set; }

        [StringLength(45)]
        public string dag { get; set; }
    }
}
