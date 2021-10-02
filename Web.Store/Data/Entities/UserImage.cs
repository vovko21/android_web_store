using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;
using Web.Store.Data.Entities.Identity;

namespace Web.Store.Data.Entities
{
    [Table("tblUserImages")]
    public class UserImage
    {
        public int Id { get; set; }
        public string Name { get; set; }
        public int Priority { get; set; }
        public virtual AppUser Product { get; set; }
    }
}
