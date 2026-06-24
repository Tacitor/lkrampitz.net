const sharp = require('sharp');

let dims = [[256, 192], [830, 623], [1150, 863], [1510, 1133], [1800, 1350], [2048, 1536]]
var src = 'RippleCarryAdderPostCarry';
var path = '/Users/Lukas/Documents/ICS/donau.ca/www/html/proj/images/';
var doLossless = true;

for (var dim = 0; dim < dims.length; dim++) {

    sharp(path + 'originals/' + src + ".png")
        .resize(dims[dim][0], dims[dim][1], {
            kernel: sharp.kernel.cubic
        })
        .webp({
            smartSubsample: true,
            preset: 'photo',
            effort: 6,
            quality: 80,
            lossless: doLossless
        })
        .toFile(path + src + '-' + dims[dim][0] + '.webp');


}
