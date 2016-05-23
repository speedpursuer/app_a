/*
 * This file provided by Facebook is for non-commercial testing and evaluation
 * purposes only.  Facebook reserves all rights not expressly granted.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
 * FACEBOOK BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN
 * ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.ionicframework.cliplayandroid329722.urlsfetcher;

/**
 * Formats of images we download from imgur.
 */
public enum ImageFormat {
  JPEG("image/jpeg"),
  PNG("image/png"),
  GIF("image/gif");

  private static final ImageFormat[] VALUES = values();

  public final String mime;

  private ImageFormat(final String mime) {
    this.mime = mime;
  }

  public static ImageFormat getImageFormatForMime(String mime) {
    for (ImageFormat type : VALUES) {
      if (type.mime.equals(mime)) {
        return type;
      }
    }
    return null;
  }
}
