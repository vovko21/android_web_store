using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Identity.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Web.Store.Data.Entities;
using Web.Store.Data.Entities.Identity;

namespace Web.Store.Data
{
    public class EFAppContext : IdentityDbContext<AppUser, AppRole, long, IdentityUserClaim<long>,
        AppUserRole, IdentityUserLogin<long>,
        IdentityRoleClaim<long>, IdentityUserToken<long>>
    {
        public EFAppContext(DbContextOptions<EFAppContext> options) :
            base(options)
        {}

        protected override void OnModelCreating(ModelBuilder builder)
        {
            builder.Entity<AppUserRole>(userRole =>
            {
                userRole.HasKey(ur => new { ur.UserId, ur.RoleId });

                userRole.HasOne(ur => ur.Role)
                    .WithMany(r => r.UserRoles)
                    .HasForeignKey(ur => ur.RoleId)
                    .IsRequired();

                userRole.HasOne(ur => ur.User)
                    .WithMany(r => r.UserRoles)
                    .HasForeignKey(ur => ur.UserId)
                    .IsRequired();
            });

            //var user = new AppUser
            //{
            //    Email = "user@gm.c",
            //    NormalizedEmail = "USER@GM.C",
            //    UserName = "Owner",
            //    NormalizedUserName = "OWNER",
            //    PhoneNumber = "+111111111111",
            //    EmailConfirmed = true,
            //    PhoneNumberConfirmed = true,
            //    SecurityStamp = Guid.NewGuid().ToString("D")
            //};

            //var password = new PasswordHasher<AppUser>();
            //var hashed = password.HashPassword(user, "secret");
            //user.PasswordHash = hashed;

            //builder.Entity<AppUser>().HasData(new[] { user });

            base.OnModelCreating(builder);
        }

        public DbSet<Product> Products { get; set; }
        public DbSet<ProductImage> ProductImages { get; set; }
        public DbSet<UserImage> UserImages { get; set; }
    }
}
