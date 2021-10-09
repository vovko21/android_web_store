using System;
using System.IO;
using System.Drawing;

namespace Web.Store.Utils
{
    public static class ImageLoader
    {
        public static string LoadBase64(string base64)
        {
            string name = Guid.NewGuid() + ".jpg";

            byte[] bytes = Convert.FromBase64String(base64);

            using (MemoryStream ms = new MemoryStream(bytes))
            {
                Image pic = Image.FromStream(ms);

                pic.Save("images/" + name);
            }

            return name;
        }
    }
}
