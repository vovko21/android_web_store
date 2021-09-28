using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Web.Store.Data;
using Web.Store.Models;

namespace Web.Store.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class ProductsController : ControllerBase
    {
        private readonly EFAppContext _context;
        public ProductsController(EFAppContext context)
        {
            _context = context;
        }
        [HttpGet("all")]
        public async Task<IActionResult> GetAll()
        {
            var list = await _context.Products.ToListAsync();
            return Ok(list);
        }

        [HttpGet("get/{id}")]
        public async Task<IActionResult> GetById(int id)
        {
            var list = await _context.ProductImages
                .Select(x=>new ProductInfoVM 
                {
                    Id=x.Id,
                    Path="/images/"+x.Name
                })
                .ToListAsync();
            return Ok(list);
        }
    }
}
