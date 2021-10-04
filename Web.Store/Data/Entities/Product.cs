using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace Web.Store.Data.Entities
{
    [Table("tblProducts")]
    public class Product
    {
        [Key]
        [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        public int Id { get; set; }
        [Required, StringLength(255)]
        public string Name { get; set; }
        public decimal Price { get; set; }
        public virtual ICollection<ProductImage> ProductImages { get; set; }
    }
}
