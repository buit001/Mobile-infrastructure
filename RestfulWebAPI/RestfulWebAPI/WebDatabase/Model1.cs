namespace WebDatabase
{
    using System;
    using System.Data.Entity;
    using System.ComponentModel.DataAnnotations.Schema;
    using System.Linq;

    public partial class Model1 : DbContext
    {
        public Model1()
            : base("name=stappenTellerDatabase")
        {
        }

        public virtual DbSet<stappen> stappen { get; set; }

        protected override void OnModelCreating(DbModelBuilder modelBuilder)
        {
            modelBuilder.Entity<stappen>()
                .Property(e => e.aantalStappen)
                .IsUnicode(false);

            modelBuilder.Entity<stappen>()
                .Property(e => e.dag)
                .IsUnicode(false);
        }
    }
}
