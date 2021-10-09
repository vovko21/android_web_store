using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Threading;
using System.Threading.Tasks;
using Web.Store.Data;
using Web.Store.Data.Entities;
using Web.Store.Models;
using Web.Store.Utils;

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
            var list = await _context.Products
                .Select(x => new PrductItemVM
                {
                    Id = x.Id,
                    Name = x.Name,
                    Price = x.Price,
                    Image = x.ProductImages
                        .Select(x => x.Name)
                        .FirstOrDefault() ?? "empty.jpg"
                })
                .ToListAsync();
            return Ok(list);
        }

        [HttpPost("add")]
        public IActionResult CreateProduct([FromBody] PrductItemVM product)
        {
            if(product.Name != null && product.Price.HasValue && product.Image != null)
            {
                string imageName = ImageLoader.LoadBase64(product.Image); 

                var newID = _context.Products.OrderByDescending(x => x.Id).FirstOrDefault();
                var newIDImage = _context.ProductImages.OrderByDescending(x => x.Id).FirstOrDefault();
                Product productDB = new Product
                {
                    Id = newID.Id + 1,
                    Name = product.Name,
                    Price = product.Price.Value,
                    ProductImages = new List<ProductImage>() {
                        new ProductImage () {
                            Id = newIDImage.Id + 1,
                            Name = imageName,
                            Priority = 1
                        }
                    }
                };

                _context.Add(productDB);
                _context.SaveChanges();
                return Ok(productDB);
            }

            if (product.Name != null && product.Price.HasValue)
            {
                var newID = _context.Products.OrderByDescending(x => x.Id).FirstOrDefault();
                Product productDB = new Product 
                {
                    Id = newID.Id + 1,
                    Name = product.Name,
                    Price = product.Price.Value
                };

                _context.Add(productDB);
                _context.SaveChanges();
                return Ok(productDB);
            }

            return BadRequest();
        }
        

        [HttpGet("get/{id}")]
        public async Task<IActionResult> GetById(int id)
        {
            var list = await _context.ProductImages
                .Select(x => new ProductInfoVM
                {
                    Id = x.Id,
                    Path = "/images/" + x.Name
                })
                .ToListAsync();
            return Ok(list);
        }
    }
}
