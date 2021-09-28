using System.ComponentModel.DataAnnotations.Schema;

namespace Web.Store.Data.Entities
{
    [Table("tblProductImages")]
    public class ProductImage
    {
        public int Id { get; set; }
        public string Name { get; set; }
        public int Priority { get; set; }
        [ForeignKey("Product")]
        public int ProductId { get; set; }
        public virtual Product Product { get; set; }
    }
}